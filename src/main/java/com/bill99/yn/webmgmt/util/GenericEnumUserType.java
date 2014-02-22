package com.bill99.yn.webmgmt.util;

import org.hibernate.HibernateException;
import org.hibernate.engine.spi.SessionImplementor;
import org.hibernate.type.BasicTypeRegistry;
import org.hibernate.type.SingleColumnType;
import org.hibernate.usertype.EnhancedUserType;
import org.hibernate.usertype.ParameterizedType;

import java.io.Serializable;
import java.lang.reflect.Method;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Properties;

/**
 * Implements a generic enum user type identified/represented by a single identifier/column.
 * <p/>
 * <ul>
 * <li>The enum type being represented by the certain user type must be set by using the 'enumClass' property.</li>
 * <li>The identifier representing a enum value is retrieved by the identifierMethod. The name of the identifier method
 * can be specified by the 'identifierMethod' property and by default the getId() method is used.</li>
 * <li>The identifier type is automatically determined by the return-type of the identifierMethod.</li>
 * <li>The valueOfMethod is the name of the static factory method returning the enumeration object being represented by
 * the given identifier. The valueOfMethod's name can be specified by setting the 'valueOfMethod' property. The default
 * valueOfMethod's name is 'fromId'.</li>
 * </ul>
 * <p/>
 * Example of an enum type represented by an int value:
 * <code>
 * <pre>
 * package com.atlassian.demo;
 *
 * public enum SimpleNumber {
 *     UNKNOWN(-1),
 *     ZERO(0),
 *     ONE(1),
 *     TWO(2),
 *     THREE(3);
 *
 *     public int getId() {
 *         return value;
 *     }
 *
 *     public SimpleNumber fromId(int value) {
 *         switch(value) {
 *             case 0: return ZERO;
 *             case 1: return ONE;
 *             case 2: return TWO;
 *             case 3: return THREE;
 *             default: return UNKNOWN;
 *         }
 *     }
 * }
 * </pre>
 * </code>
 * <p/>
 * Using JPA, the mapping would look like this:
 * <code>
 * <pre>
 * &#064;Type(type = "com.atlassian.stash.internal.hibernate.GenericEnumUserType", parameters = {
 *     &#064;Parameter(name = "enumClass", value = "com.atlassian.demo.SimpleNumber"),
 *     &#064;Parameter(name = "identifierMethod", value = "getId"),
 *     &#064;Parameter(name = "valueOfMethod", value = "fromId")})
 * private SimpleNumber randomNumber;
 * </pre>
 * </code>
 * <p/>
 * In this example, the properties for the {@code GenericEnumUserType} are fully specified to make the example more
 * clear on the type's usage. However, because the identifier and valueOf methods follow standard naming, properties
 * for them may be omitted from the mapping to reduce configuration.
 *
 * @since 4.0
 */
@SuppressWarnings("unused")
public class GenericEnumUserType implements EnhancedUserType, ParameterizedType
{
    private static final String DEFAULT_IDENTIFIER_METHOD_NAME = "getId";
    private static final String DEFAULT_VALUE_OF_METHOD_NAME = "fromId";
    private static final Class[] NULL_CLASS_VARARG = null;
    private static final Object[] NULL_OBJECT_VARARG = null;
    private static final char SINGLE_QUOTE = '\'';

    private Class<? extends Enum> enumClass;
    private Method identifierMethod;
    private int[] sqlTypes;
    private SingleColumnType<Object> type;
    private Method valueOfMethod;

    public Object assemble(Serializable cached, Object owner) throws HibernateException
    {
        return cached;
    }

    public Object deepCopy(Object value) throws HibernateException
    {
        return value;
    }

    public Serializable disassemble(Object value) throws HibernateException
    {
        return (Serializable) value;
    }

    public boolean equals(Object x, Object y) throws HibernateException
    {
        return x == y;
    }

    public Object fromXMLString(String xmlValue)
    {
        return Enum.valueOf(enumClass, xmlValue);
    }

    public int hashCode(Object x) throws HibernateException
    {
        return x.hashCode();
    }

    public boolean isMutable()
    {
        return false;
    }

    public Object nullSafeGet(ResultSet rs, String[] names, SessionImplementor session, Object owner)
            throws HibernateException, SQLException
    {
        Object identifier = type.nullSafeGet(rs, names[0], session);
        if (identifier == null || rs.wasNull())
        {
            return null;
        }
        try
        {
            return valueOfMethod.invoke(enumClass, identifier);
        }
        catch (Exception exception)
        {
            String msg = "Exception while invoking valueOfMethod [" + valueOfMethod.getName() +
                    "] of Enum class [" + enumClass.getName() + "] with argument of type [" +
                    identifier.getClass().getName() + "], value=[" + identifier + "]";
            throw new HibernateException(msg, exception);
        }
    }

    public void nullSafeSet(PreparedStatement st, Object value, int index, SessionImplementor session)
            throws HibernateException, SQLException
    {
        if (value == null)
        {
            st.setNull(index, sqlTypes[0]);
        }
        else
        {
            try
            {
                Object identifier = identifierMethod.invoke(value, NULL_OBJECT_VARARG);
                type.set(st, identifier, index, session);
            }
            catch (Exception exception)
            {
                String msg = "Exception while invoking identifierMethod [" + identifierMethod.getName() +
                        "] of Enum class [" + enumClass.getName() +
                        "] with argument of type [" + value.getClass().getName() + "], value=[" + value + "]";
                throw new HibernateException(msg, exception);
            }
        }
    }

    public String objectToSQLString(Object value)
    {
        return SINGLE_QUOTE + ((Enum) value).name() + SINGLE_QUOTE;
    }

    public Object replace(Object original, Object target, Object owner) throws HibernateException
    {
        return original;
    }

    public Class returnedClass()
    {
        return enumClass;
    }

    @SuppressWarnings("unchecked")
    public void setParameterValues(Properties parameters)
    {
        String enumClassName = parameters.getProperty("enumClass");
        try
        {
            enumClass = Class.forName(enumClassName).asSubclass(Enum.class);
        }
        catch (ClassNotFoundException exception)
        {
            throw new HibernateException("Enum class not found", exception);
        }

        String identifierMethodName = parameters.getProperty("identifierMethod", DEFAULT_IDENTIFIER_METHOD_NAME);
        try
        {
            identifierMethod = enumClass.getMethod(identifierMethodName, NULL_CLASS_VARARG);
        }
        catch (Exception exception)
        {
            throw new HibernateException("Failed to obtain identifier method", exception);
        }
        Class<?> identifierType = identifierMethod.getReturnType();

        String valueOfMethodName = parameters.getProperty("valueOfMethod", DEFAULT_VALUE_OF_METHOD_NAME);
        try
        {
            valueOfMethod = enumClass.getMethod(valueOfMethodName, identifierType);
        }
        catch (Exception exception)
        {
            throw new HibernateException("Failed to obtain valueOf method", exception);
        }

        //TODO: We really shouldn't be instantiating this, but I don't know how to get the SessionImplementor here
        BasicTypeRegistry registry = new BasicTypeRegistry();

        type = (SingleColumnType<Object>) registry.getRegisteredType(identifierType.getName());
        if (type == null)
        {
            throw new HibernateException("Unsupported identifier type " + identifierType.getName());
        }
        sqlTypes = new int[]{type.sqlType()};
    }

    public int[] sqlTypes()
    {
        return sqlTypes;
    }

    public String toXMLString(Object value)
    {
        return ((Enum<?>) value).name();
    }
}