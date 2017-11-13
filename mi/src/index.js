import App from './app';
import HeaderComponent from "./components/header/header.component";
import MainComponent from "./components/main/main.component";
import FooterComponent from "./components/footer/footer.component";
import AboutComponent from "./components/about/about.component";
import OffersComponent from "./components/offers/offers.component";


function bootstrap() {
    new App({
        components: [HeaderComponent, MainComponent, FooterComponent, AboutComponent, OffersComponent],
        styles: require('./styles/app.scss')
    });
}

document.addEventListener("DOMContentLoaded", function() {
    bootstrap();
});
