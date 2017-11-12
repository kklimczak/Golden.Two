import App from './app';
import HeaderComponent from "./components/header/header.component";


function bootstrap() {
    new App({
        components: [HeaderComponent],
        styles: require('./styles/app.scss')
    });
}

document.addEventListener("DOMContentLoaded", function() {
    bootstrap();
});
