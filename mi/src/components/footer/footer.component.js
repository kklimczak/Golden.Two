import Component from '../abstract/component';

class FooterComponent extends Component {

    constructor() {
        super({
            template: require('./footer.component.handlebars'),
            styles: require('./footer.component.scss'),
            tag: 'footer',
            data: {
                name: 'example'
            }
        });


    }

    init() {
    }

}

export default FooterComponent;

