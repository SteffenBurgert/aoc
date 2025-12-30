import {Pipe, PipeTransform} from '@angular/core';

@Pipe({
  standalone: true,
  name: 'orDash',
})
export class OrDashPipe implements PipeTransform {
  transform(value: string | number | boolean | undefined | null): string {
    return value === undefined || value === null || value === '' ? '-' : value.toString();
  }
}

@Pipe({
  standalone: true,
  name: 'unsingedNumberOrDash',
})
export class UnsignedNumberOrDashPipe implements PipeTransform {
  transform(value: number | undefined | null): string {
    return value === null || value === undefined || value === -1 ? '-' : value.toString();
  }
}
