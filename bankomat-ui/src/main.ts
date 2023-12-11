import { platformBrowserDynamic } from '@angular/platform-browser-dynamic';
import './global-shims'

import { AppModule } from './app/app.module';


platformBrowserDynamic().bootstrapModule(AppModule)
  .catch(err => console.error(err));
