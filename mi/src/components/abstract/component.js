export default class Component {

    constructor(config) {
        this.setConfiguration(config);
        this.loadTemplate();
        this.init();
    }

    setConfiguration(config) {
        this.data = config.data;
        this.template = config.template;
        this.tag = config.tag;
        this.styles = config.styles;
    }

    loadTemplate() {

        let elements = document.querySelectorAll(`[data-${this.tag}]`);

        [].forEach.call(elements, (element) => {
            let div = document.createElement('div');

            div.innerHTML = this.template(this.data);

            while (element.firstChild) {
                element.removeChild(element.firstChild);
            }

            element.appendChild(div);
        })

    }

    init() {

    }
}