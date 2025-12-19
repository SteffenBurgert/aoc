import {Pipe, PipeTransform} from '@angular/core';

@Pipe({
  standalone: true,
  name: 'check',
})
export class SolutionCheckPipe implements PipeTransform {
  transform(value: boolean | undefined): string {
    return typeof value === 'boolean' ? value ? 'Correct' : 'Incorrect' : '';
  }
}
