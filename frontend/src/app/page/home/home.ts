import {Component} from '@angular/core';
import {environment} from '../../../environment/environment';

@Component({
  selector: 'aoc-home',
  standalone: true,
  imports: [],
  templateUrl: './home.html',
  styleUrl: './home.scss',
})
export class Home {
  protected readonly environment = environment;
}
