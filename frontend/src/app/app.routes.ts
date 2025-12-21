import {Routes} from '@angular/router';
import {Home} from './page/home/home';
import {Parser} from './page/parser/parser';
import {Imprint} from './page/imprint/imprint';
import {PrivacyPolicy} from './page/privacy-policy/privacy-policy';

export const routes: Routes = [
  {
    path: '',
    component: Home
  },
  {
    path: 'parser',
    component: Parser
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
