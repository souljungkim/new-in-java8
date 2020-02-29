var MyJavaClass = Java.type('my.package.MyJavaClass');

// call the static method
var greetingResult = MyJavaClass.sayHello('John Doe');
print(greetingResult);

// create a new intance of MyJavaClass
var myClass = new MyJavaClass();
var calcResult = myClass.add(1, 2);
print(calcResult);

// Hello John Doe from Java!
// 3