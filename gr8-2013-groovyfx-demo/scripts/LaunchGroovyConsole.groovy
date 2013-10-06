import groovy.ui.Console

assessments = new SerializationUtil().deserializeObjectFromFile('test-assessments.ser')

Console console = new Console();
console.setVariable('assessments', assessments);
console.run();