export default class App {

    constructor(options) {
        this.register();
        options.components.forEach(component => this.component(component));
        this.run();

    }

    register() {
        this.components = [];
    }

    component(component) {
        this.components.push(component);
    }

    run() {
        this.components.forEach((value) => {
        new value();
    })
}

}