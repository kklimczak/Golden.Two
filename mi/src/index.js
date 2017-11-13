import App from './app';
import HeaderComponent from "./components/header/header.component";
import MainComponent from "./components/main/main.component";
import FooterComponent from "./components/footer/footer.component";
import AboutComponent from "./components/about/about.component";


function bootstrap() {
    new App({
        components: [HeaderComponent, MainComponent, FooterComponent, AboutComponent],
        styles: require('./styles/app.scss')
    });
}

document.addEventListener("DOMContentLoaded", function() {
    bootstrap();
});
