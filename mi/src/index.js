import App from './app';
import HeaderComponent from "./components/header/header.component";
import MainComponent from "./components/main/main.component";
import FooterComponent from "./components/footer/footer.component";


function bootstrap() {
    new App({
        components: [HeaderComponent, MainComponent, FooterComponent],
        styles: require('./styles/app.scss')
    });
}

document.addEventListener("DOMContentLoaded", function() {
    bootstrap();
});
