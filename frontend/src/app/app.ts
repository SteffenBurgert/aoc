import {Component} from '@angular/core';
import {RouterLink, RouterOutlet} from '@angular/router';
import {MatButton} from '@angular/material/button';

@Component({
  selector: 'app-root',
  imports: [RouterOutlet, MatButton, RouterLink],
  templateUrl: './app.html',
  styleUrl: './app.scss'
})
export class App {
  currentYear = new Date().getFullYear();
}
