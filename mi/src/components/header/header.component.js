import Component from '../abstract/component';

class HeaderComponent extends Component {

    constructor() {
        super({
            template: require('./header.component.handlebars'),
            styles: require('./header.component.scss'),
            tag: 'header',
            data: {
                name: 'example'
            }
        });


    }

    init() {
    }

}

export default HeaderComponent;

