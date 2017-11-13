import Component from '../abstract/component';

class OffersComponent extends Component {

    constructor() {
        super({
            template: require('./offers.component.handlebars'),
            styles: require('./offers.component.scss'),
            tag: 'offers',
            data: {
                name: 'example'
            }
        });


    }

    init() {
    }

}

export default OffersComponent;

