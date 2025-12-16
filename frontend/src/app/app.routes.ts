import { Routes } from '@angular/router';
import {Home} from './page/home/home';
import {Serialization} from './page/serialization/serialization';
import {Imprint} from './page/imprint/imprint';
import {PrivacyPolicy} from './page/privacy-policy/privacy-policy';

export const routes: Routes = [
  {
    path: '',
    component: Home
  },
  {
    path: 'serialization',
    component: Serialization
  },
  {
    path: 'imprint',
    component: Imprint
  },
  {
    path: 'privacy-policy',
    component: PrivacyPolicy
  }
];
