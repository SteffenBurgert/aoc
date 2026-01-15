import {Component} from '@angular/core';
import {RouterLink, RouterOutlet} from '@angular/router';
import {MatButton} from '@angular/material/button';
import {NgOptimizedImage} from '@angular/common';

@Component({
  selector: 'aoc-root',
  standalone: true,
  imports: [RouterOutlet, MatButton, RouterLink, NgOptimizedImage],
  templateUrl: './app.html',
  styleUrl: './app.scss'
})
export class App {
  currentYear = new Date().getFullYear();
}
