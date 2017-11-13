import Component from '../abstract/component';

class ContactComponent extends Component {

    constructor() {
        super({
            template: require('./contact.component.handlebars'),
            styles: require('./contact.component.scss'),
            tag: 'contact',
            data: {
                name: 'example'
            }
        });


    }

    init() {
    }

}

export default ContactComponent;

