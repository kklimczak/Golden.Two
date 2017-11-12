import Component from '../abstract/component';

class MainComponent extends Component {

    constructor() {
        super({
            template: require('./main.component.handlebars'),
            styles: require('./main.component.scss'),
            tag: 'main',
            data: {
                name: 'example'
            }
        });


    }

    init() {
    }

}

export default MainComponent;

