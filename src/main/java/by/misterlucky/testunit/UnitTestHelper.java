package by.misterlucky.testunit;

import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.sql.Date;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Queue;
import java.util.Set;

import junit.framework.AssertionFailedError;

public class UnitTestHelper {
	private static final String BOOLEAN = "boolean";
	private static final String BYTE = "byte";
	private static final String SHORT = "short";
	private static final String FLOAT = "float";
	private static final String DOUBLE = "double";
	private static final String INT = "int";
	private static final String LONG = "long";
	private static final String CHAR = "char";

	private static final String STRING = "java.lang.String";
	private static final String LIST = "java.util.List";
	private static final String SET = "java.util.Set";
	private static final String MAP = "java.util.Map";
	private static final String QUEUE = "java.util.Queue";

	private static final String BYTEWRAPPER = "java.lang.Byte";
	private static final String INTWRAPPER = "java.lang.Integer";
	private static final String LONGWRAPPER = "java.lang.Long";
	private static final String DOUBLEWRAPPER = "java.lang.Double";
	private static final String CHARWRAPPER = "java.lang.Character";
	private static final String BOOLEANWRAPPER = "java.lang.Boolean";
	private static final String SQLDATE = "java.sql.Date";

	private static final Set<String> SIMPLETYPES = new HashSet<>();
	static {
		SIMPLETYPES.addAll(Arrays.asList("boolean", "byte", "short", "float", "double", "int", "long", "char"));
	}

	private static final int PRIVATE = 2;

	public static void testAsClassicPojo(Class<?> clazz) throws Exception {
		assertAllDeclaredFieldsPrivateAndNotStatic(clazz);
		getSetTestForAllFields(clazz);
	}

	public static void getSetTestForAllFields(Class<?> clazz) throws Exception {
		getSetTestForAllFieldsExcept(clazz);
	}

	public static void getSetTestForAllFieldsExcept(Class<?> clazz, String... fields) throws Exception {
		Map<String, Method> methods = new HashMap<>();
		Set<String> exceptFields = new HashSet<>(Arrays.asList(fields));
		for (Method m : clazz.getMethods()) {
			methods.put(m.getName().toUpperCase(), m);
		}
		for (Field f : clazz.getDeclaredFields()) {
			if (exceptFields.contains(f.getName())) {
				continue;
			}
			switch (f.getType().getName()) {
			case BOOLEAN:
				testBooleanSimpleTypeField(clazz, f, methods.get("IS" + f.getName().toUpperCase()),
						methods.get("SET" + f.getName().toUpperCase()));
				break;
			case BYTE:
				testByteSimpleTypeField(clazz, f, methods.get("GET" + f.getName().toUpperCase()),
						methods.get("SET" + f.getName().toUpperCase()));
				break;
			case SHORT:
				testShortSimpleTypeField(clazz, f, methods.get("GET" + f.getName().toUpperCase()),
						methods.get("SET" + f.getName().toUpperCase()));
				break;
			case FLOAT:
				testFloatSimpleTypeField(clazz, f, methods.get("GET" + f.getName().toUpperCase()),
						methods.get("SET" + f.getName().toUpperCase()));
				break;
			case DOUBLE:
				testDoubleSimpleTypeField(clazz, f, methods.get("GET" + f.getName().toUpperCase()),
						methods.get("SET" + f.getName().toUpperCase()));
				break;
			case INT:
				testIntSimpleTypeField(clazz, f, methods.get("GET" + f.getName().toUpperCase()),
						methods.get("SET" + f.getName().toUpperCase()));
				break;
			case LONG:
				testLongSimpleTypeField(clazz, f, methods.get("GET" + f.getName().toUpperCase()),
						methods.get("SET" + f.getName().toUpperCase()));
				break;
			case CHAR:
				testCharSimpleTypeField(clazz, f, methods.get("GET" + f.getName().toUpperCase()),
						methods.get("SET" + f.getName().toUpperCase()));
				break;
			case STRING:
				testStringField(clazz, f, methods.get("GET" + f.getName().toUpperCase()),
						methods.get("SET" + f.getName().toUpperCase()));
				break;
			case LIST:
				testListField(clazz, f, methods.get("GET" + f.getName().toUpperCase()),
						methods.get("SET" + f.getName().toUpperCase()));
				break;
			case SET:
				testSetField(clazz, f, methods.get("GET" + f.getName().toUpperCase()),
						methods.get("SET" + f.getName().toUpperCase()));
				break;
			case QUEUE:
				testQueueField(clazz, f, methods.get("GET" + f.getName().toUpperCase()),
						methods.get("SET" + f.getName().toUpperCase()));
				break;
			case MAP:
				testMapField(clazz, f, methods.get("GET" + f.getName().toUpperCase()),
						methods.get("SET" + f.getName().toUpperCase()));
				break;
			case BOOLEANWRAPPER:
				testBooleanWrapperField(clazz, f, methods.get("GET" + f.getName().toUpperCase()),
						methods.get("SET" + f.getName().toUpperCase()));
				break;
			case BYTEWRAPPER:
				testByteWrapperField(clazz, f, methods.get("GET" + f.getName().toUpperCase()),
						methods.get("SET" + f.getName().toUpperCase()));
				break;
			case INTWRAPPER:
				testIntWrapperField(clazz, f, methods.get("GET" + f.getName().toUpperCase()),
						methods.get("SET" + f.getName().toUpperCase()));
				break;
			case LONGWRAPPER:
				testLongWrapperField(clazz, f, methods.get("GET" + f.getName().toUpperCase()),
						methods.get("SET" + f.getName().toUpperCase()));
				break;
			case DOUBLEWRAPPER:
				testDoubleWrapperField(clazz, f, methods.get("GET" + f.getName().toUpperCase()),
						methods.get("SET" + f.getName().toUpperCase()));
				break;
			case CHARWRAPPER:
				testCharWrapperField(clazz, f, methods.get("GET" + f.getName().toUpperCase()),
						methods.get("SET" + f.getName().toUpperCase()));
				break;
			case SQLDATE:
				testSqlDateField(clazz, f, methods.get("GET" + f.getName().toUpperCase()),
						methods.get("SET" + f.getName().toUpperCase()));
				break;
			default:
				testFieldAsOthersType(clazz, f, methods.get("GET" + f.getName().toUpperCase()),
						methods.get("SET" + f.getName().toUpperCase()));
			}
		}

	}

	public static void getSetForFieldWithGivenInstanceToSet(Class<?> clazz, String fieldName, Object instance)
			throws Exception {
		Field field = clazz.getDeclaredField(fieldName);
		Method getter = clazz
				.getDeclaredMethod("get" + fieldName.substring(0, 1).toUpperCase() + fieldName.substring(1));
		Method setter = clazz.getDeclaredMethod(
				"set" + fieldName.substring(0, 1).toUpperCase() + fieldName.substring(1), getter.getReturnType());
		Object ob = clazz.newInstance();
		if (getter.invoke(ob) != null) {
			throw invalidInitialization(field);
		}
		setter.invoke(ob, instance);
		if (!instance.equals(getter.invoke(ob))) {
			throw new AssertionFailedError("Unaccepted setter`s behavior for field : " + field.getName());
		}
	}

	public static void assertAllDeclaredFieldsPrivateAndNotStatic(Class<?> clazz) {
		for (Field f : clazz.getDeclaredFields()) {
			if (f.getModifiers() != PRIVATE) {
				throw new AssertionFailedError(
						"the class " + clazz.getCanonicalName() + " has field with unallowed modifier");
			}
		}
	}

	protected static boolean isSimpleType(Field field) {
		return SIMPLETYPES.contains(field.getType().getCanonicalName());
	}
	
	private static void testSqlDateField(Class<?> clazz, Field field, Method getter, Method setter)
			throws Exception {
		checkGetterSetterArePresent(field, getter, setter);
		Object ob = clazz.newInstance();
		if (getter.invoke(ob) != null) {
			throw invalidInitialization(field);
		}
		java.sql.Date date = new Date((long)(Math.random()*Long.MAX_VALUE));
		setter.invoke(ob, date);
		if (!date.equals(getter.invoke(ob))) {
			throw new AssertionFailedError("Unaccepted setter`s behavior for field : " + field.getName());
		}
	}

	private static void testBooleanSimpleTypeField(Class<?> clazz, Field field, Method getter, Method setter)
			throws Exception {
		checkGetterSetterArePresent(field, getter, setter);
		Object ob = clazz.newInstance();
		if ((Boolean) getter.invoke(ob)) {
			throw invalidInitialization(field);
		}
		setter.invoke(ob, Boolean.TRUE);
		if (!(Boolean) getter.invoke(ob)) {
			throw new AssertionFailedError("Unaccepted setter`s behavior for field : " + field.getName());
		}
		setter.invoke(ob, Boolean.FALSE);
		if ((Boolean) getter.invoke(ob)) {
			throw invalidGetterOrSetterBehavior(field);
		}
	}

	private static void testBooleanWrapperField(Class<?> clazz, Field field, Method getter, Method setter)
			throws Exception {
		checkGetterSetterArePresent(field, getter, setter);
		Object ob = clazz.newInstance();
		if (getter.invoke(ob) != null) {
			throw invalidInitialization(field);
		}
		setter.invoke(ob, Boolean.TRUE);
		if (!(Boolean) getter.invoke(ob)) {
			throw new AssertionFailedError("Unaccepted setter`s behavior for field : " + field.getName());
		}
		setter.invoke(ob, Boolean.FALSE);
		if ((Boolean) getter.invoke(ob)) {
			throw invalidGetterOrSetterBehavior(field);
		}
	}

	private static void testByteSimpleTypeField(Class<?> clazz, Field field, Method getter, Method setter)
			throws Exception {
		checkGetterSetterArePresent(field, getter, setter);
		Object ob = clazz.newInstance();
		if (((Byte) getter.invoke(ob)).byteValue() != 0) {
			throw invalidInitialization(field);
		}
		byte value = (byte) (Byte.MAX_VALUE * Math.random());
		setter.invoke(ob, value);
		if (((Byte) getter.invoke(ob)).byteValue() != value) {
			throw invalidGetterOrSetterBehavior(field);
		}
	}

	private static void testByteWrapperField(Class<?> clazz, Field field, Method getter, Method setter)
			throws Exception {
		checkGetterSetterArePresent(field, getter, setter);
		Object ob = clazz.newInstance();
		if (getter.invoke(ob) != null) {
			throw invalidInitialization(field);
		}
		Byte value = (byte) (Byte.MAX_VALUE * Math.random());
		setter.invoke(ob, value);
		if (!value.equals(getter.invoke(ob))) {
			throw invalidGetterOrSetterBehavior(field);
		}
	}

	private static void testShortSimpleTypeField(Class<?> clazz, Field field, Method getter, Method setter)
			throws Exception {
		checkGetterSetterArePresent(field, getter, setter);
		Object ob = clazz.newInstance();
		if (((Short) getter.invoke(ob)).shortValue() != 0) {
			throw invalidInitialization(field);
		}
		short value = (short) (Short.MAX_VALUE * Math.random());
		setter.invoke(ob, value);
		if (((Short) getter.invoke(ob)).shortValue() != value) {
			throw invalidGetterOrSetterBehavior(field);
		}
	}

	private static void testIntSimpleTypeField(Class<?> clazz, Field field, Method getter, Method setter)
			throws Exception {
		checkGetterSetterArePresent(field, getter, setter);
		Object ob = clazz.newInstance();
		if (((Integer) getter.invoke(ob)).intValue() != 0) {
			throw invalidInitialization(field);
		}
		int value = (int) (Integer.MAX_VALUE * Math.random());
		setter.invoke(ob, value);
		if (((Integer) getter.invoke(ob)).intValue() != value) {
			throw invalidGetterOrSetterBehavior(field);
		}
	}

	private static void testIntWrapperField(Class<?> clazz, Field field, Method getter, Method setter)
			throws Exception {
		checkGetterSetterArePresent(field, getter, setter);
		Object ob = clazz.newInstance();
		if (getter.invoke(ob) != null) {
			throw invalidInitialization(field);
		}
		Integer value = new Integer((int) (Integer.MAX_VALUE * Math.random()));
		setter.invoke(ob, value);
		if (!value.equals(getter.invoke(ob))) {
			throw invalidGetterOrSetterBehavior(field);
		}
	}

	private static void testLongSimpleTypeField(Class<?> clazz, Field field, Method getter, Method setter)
			throws Exception {
		checkGetterSetterArePresent(field, getter, setter);
		Object ob = clazz.newInstance();
		if (((Long) getter.invoke(ob)).longValue() != 0) {
			throw invalidInitialization(field);
		}
		long value = (long) (Long.MAX_VALUE * Math.random());
		setter.invoke(ob, value);
		if (((Long) getter.invoke(ob)).longValue() != value) {
			throw invalidGetterOrSetterBehavior(field);
		}
	}

	private static void testLongWrapperField(Class<?> clazz, Field field, Method getter, Method setter)
			throws Exception {
		checkGetterSetterArePresent(field, getter, setter);
		Object ob = clazz.newInstance();
		if (getter.invoke(ob) != null) {
			throw invalidInitialization(field);
		}
		Long value = new Long((long) (Long.MAX_VALUE * Math.random()));
		setter.invoke(ob, value);
		if (!value.equals(getter.invoke(ob))) {
			throw invalidGetterOrSetterBehavior(field);
		}
	}

	private static void testFloatSimpleTypeField(Class<?> clazz, Field field, Method getter, Method setter)
			throws Exception {
		checkGetterSetterArePresent(field, getter, setter);
		Object ob = clazz.newInstance();
		if (((Long) getter.invoke(ob)).longValue() != 0) {
			throw invalidInitialization(field);
		}
		float value = (float) (Float.MAX_VALUE * Math.random());
		setter.invoke(ob, value);
		if (((Float) getter.invoke(ob)).floatValue() != value) {
			throw invalidGetterOrSetterBehavior(field);
		}
	}

	private static void testDoubleSimpleTypeField(Class<?> clazz, Field field, Method getter, Method setter)
			throws Exception {
		checkGetterSetterArePresent(field, getter, setter);
		Object ob = clazz.newInstance();
		if (((Double) getter.invoke(ob)).doubleValue() != 0) {
			throw invalidInitialization(field);
		}
		double value = Double.MAX_VALUE * Math.random();
		setter.invoke(ob, value);
		if (((Double) getter.invoke(ob)).doubleValue() != value) {
			throw invalidGetterOrSetterBehavior(field);
		}
	}

	private static void testDoubleWrapperField(Class<?> clazz, Field field, Method getter, Method setter)
			throws Exception {
		checkGetterSetterArePresent(field, getter, setter);
		Object ob = clazz.newInstance();
		if (getter.invoke(ob) != null) {
			throw invalidInitialization(field);
		}
		Double value = new Double(Long.MAX_VALUE * Math.random());
		setter.invoke(ob, value);
		if (!value.equals(getter.invoke(ob))) {
			throw invalidGetterOrSetterBehavior(field);
		}
	}

	private static void testCharSimpleTypeField(Class<?> clazz, Field field, Method getter, Method setter)
			throws Exception {
		checkGetterSetterArePresent(field, getter, setter);
		Object ob = clazz.newInstance();
		if (((Character) getter.invoke(ob)).charValue() != (char) 0) {
			throw invalidInitialization(field);
		}
		char value = (char) (Short.MAX_VALUE * Math.random());
		setter.invoke(ob, value);
		if (((Character) getter.invoke(ob)).charValue() != value) {
			throw invalidGetterOrSetterBehavior(field);
		}
	}

	private static void testCharWrapperField(Class<?> clazz, Field field, Method getter, Method setter)
			throws Exception {
		checkGetterSetterArePresent(field, getter, setter);
		Object ob = clazz.newInstance();
		if (getter.invoke(ob) != null) {
			throw invalidInitialization(field);
		}
		Character value = new Character((char) (Short.MAX_VALUE * Math.random()));
		setter.invoke(ob, value);
		if (!value.equals(getter.invoke(ob))) {
			throw invalidGetterOrSetterBehavior(field);
		}
	}

	private static void testStringField(Class<?> clazz, Field field, Method getter, Method setter) throws Exception {
		checkGetterSetterArePresent(field, getter, setter);
		Object ob = clazz.newInstance();
		if (getter.invoke(ob) != null) {
			throw invalidInitialization(field);
		}
		String line = Double.toHexString(Double.MAX_VALUE * Math.random());
		setter.invoke(ob, line);
		if (!line.equals(getter.invoke(ob))) {
			throw invalidGetterOrSetterBehavior(field);
		}
	}

	private static void testFieldAsOthersType(Class<?> clazz, Field field, Method getter, Method setter)
			throws Exception {
		checkGetterSetterArePresent(field, getter, setter);
		Object ob = clazz.newInstance();
		if (getter.invoke(ob) != null) {
			throw invalidInitialization(field);
		}
		Object value = field.getType().newInstance();
		setter.invoke(ob, value);
		if (!value.equals(getter.invoke(ob))) {
			throw invalidGetterOrSetterBehavior(field);
		}
	}

	private static void testListField(Class<?> clazz, Field field, Method getter, Method setter) throws Exception {
		checkGetterSetterArePresent(field, getter, setter);
		Object ob = clazz.newInstance();
		if (getter.invoke(ob) != null) {
			throw invalidInitialization(field);
		}
		@SuppressWarnings("rawtypes")
		List list = arrayList(field.getType().getTypeParameters().getClass());
		setter.invoke(ob, list);
		if (list != (getter.invoke(ob))) {
			throw invalidGetterOrSetterBehavior(field);
		}
	}

	private static void testSetField(Class<?> clazz, Field field, Method getter, Method setter) throws Exception {
		checkGetterSetterArePresent(field, getter, setter);
		Object ob = clazz.newInstance();
		if (getter.invoke(ob) != null) {
			throw invalidInitialization(field);
		}
		@SuppressWarnings("rawtypes")
		Set set = hashSet(field.getType().getTypeParameters().getClass());
		setter.invoke(ob, set);
		if (set != (getter.invoke(ob))) {
			throw invalidGetterOrSetterBehavior(field);
		}
	}

	private static void testQueueField(Class<?> clazz, Field field, Method getter, Method setter) throws Exception {
		checkGetterSetterArePresent(field, getter, setter);
		Object ob = clazz.newInstance();
		if (getter.invoke(ob) != null) {
			throw invalidInitialization(field);
		}
		@SuppressWarnings("rawtypes")
		Queue queue = linkedList(field.getType().getTypeParameters().getClass());
		setter.invoke(ob, queue);
		if (queue != (getter.invoke(ob))) {
			throw invalidGetterOrSetterBehavior(field);
		}
	}

	private static void testMapField(Class<?> clazz, Field field, Method getter, Method setter) throws Exception {
		checkGetterSetterArePresent(field, getter, setter);
		Object ob = clazz.newInstance();
		if (getter.invoke(ob) != null) {
			throw invalidInitialization(field);
		}
		@SuppressWarnings("rawtypes")
		Map map = hashMap(field.getType().getTypeParameters()[0].getClass(),
				field.getType().getTypeParameters()[1].getClass());
		setter.invoke(ob, map);
		if (map != (getter.invoke(ob))) {
			throw invalidGetterOrSetterBehavior(field);
		}
	}

	protected static boolean isBooleanSimpleTypeField(Field field) {
		return BOOLEAN.equals(field.getType().getCanonicalName());
	}

	protected static boolean isByteSimpleTypeField(Field field) {
		return BYTE.equals(field.getType().getCanonicalName());
	}

	protected static boolean isShortSimpleTypeField(Field field) {
		return SHORT.equals(field.getType().getCanonicalName());
	}

	protected static boolean isIntSimpleTypeField(Field field) {
		return INT.equals(field.getType().getCanonicalName());
	}

	protected static boolean isLongSimpleTypeField(Field field) {
		return LONG.equals(field.getType().getCanonicalName());
	}

	protected static boolean isFloatSimpleTypeField(Field field) {
		return FLOAT.equals(field.getType().getCanonicalName());
	}

	protected static boolean isDoubleSimpleTypeField(Field field) {
		return DOUBLE.equals(field.getType().getCanonicalName());
	}

	protected static boolean isCharSimpleTypeField(Field field) {
		return CHAR.equals(field.getType().getCanonicalName());
	}

	protected static boolean isStringField(Field field) {
		return STRING.equals(field.getType().getCanonicalName());
	}

	private static void checkGetterSetterArePresent(Field field, Method getter, Method setter) {
		if (getter == null) {
			throw noGetter(field);
		}
		if (setter == null) {
			throw noSetter(field);
		}
	}

	private static AssertionFailedError invalidInitialization(Field field) {
		return new AssertionFailedError(
				"Unaccepted initialization or unexpected getter`s behavior for field: " + field.getName());
	}

	private static AssertionFailedError noGetter(Field field) {
		return new AssertionFailedError("No getter found for field: " + field.getName());
	}

	private static AssertionFailedError noSetter(Field field) {
		return new AssertionFailedError("No setter found for field: " + field.getName());
	}

	private static AssertionFailedError invalidGetterOrSetterBehavior(Field field) {
		return new AssertionFailedError("Unexpected getter`s or setter`s behavior for field: " + field.getName());
	}

	protected static <T> List<T> arrayList(Class<T> parameter) {
		return new ArrayList<>();
	}

	protected static <T> Set<T> hashSet(Class<T> parameter) {
		return new HashSet<>();
	}

	protected static <T> Queue<T> linkedList(Class<T> parameter) {
		return new LinkedList<>();
	}

	protected static <K, V> Map<K, V> hashMap(Class<K> key, Class<V> value) {
		return new HashMap<>();
	}

}
