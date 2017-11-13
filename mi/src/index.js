import App from './app';
import HeaderComponent from "./components/header/header.component";
import MainComponent from "./components/main/main.component";
import FooterComponent from "./components/footer/footer.component";
import AboutComponent from "./components/about/about.component";
import OffersComponent from "./components/offers/offers.component";
import ContactComponent from "./components/contact/contact.component";


function bootstrap() {
    new App({
        components: [HeaderComponent, MainComponent, FooterComponent, AboutComponent, OffersComponent, ContactComponent],
        styles: require('./styles/app.scss')
    });
}

document.addEventListener("DOMContentLoaded", function() {
    bootstrap();
});
