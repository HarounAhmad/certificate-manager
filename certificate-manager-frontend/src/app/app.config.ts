import {ApplicationConfig, importProvidersFrom, provideZoneChangeDetection} from '@angular/core';
import { provideRouter } from '@angular/router';

import { routes } from './app.routes';
import {providePrimeNG} from "primeng/config";
import {provideAnimations} from "@angular/platform-browser/animations";
import Aura from '@primeng/themes/aura';
import {HttpClientModule} from "@angular/common/http";

export const appConfig: ApplicationConfig = {
  providers: [provideZoneChangeDetection(
    { eventCoalescing: true }),
    provideRouter(routes),
    providePrimeNG({
      ripple: true,
      theme: {
        preset: Aura,

      }
    }),
    importProvidersFrom(HttpClientModule),
    provideAnimations()
  ]
};
