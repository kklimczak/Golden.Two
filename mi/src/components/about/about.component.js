import Component from '../abstract/component';

class AboutComponent extends Component {

    constructor() {
        super({
            template: require('./about.component.handlebars'),
            styles: require('./about.component.scss'),
            tag: 'about',
            data: {
                name: 'example'
            }
        });


    }

    init() {
    }

}

export default AboutComponent;

