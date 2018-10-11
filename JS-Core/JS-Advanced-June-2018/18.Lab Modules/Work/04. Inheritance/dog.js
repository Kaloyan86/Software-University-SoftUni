let Entity = require('./entity');

class Dog extends Entity {
    constructor(name){
        super(name)
    }
    saySomething(){
        return this.name + 'barts!';
    }
}

module.exports = Dog;