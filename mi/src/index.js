import App from './app';
import HeaderComponent from "./components/header/header.component";
import MainComponent from "./components/main/main.component";


function bootstrap() {
    new App({
        components: [HeaderComponent, MainComponent],
        styles: require('./styles/app.scss')
    });
}

document.addEventListener("DOMContentLoaded", function() {
    bootstrap();
});
