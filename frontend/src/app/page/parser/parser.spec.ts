import {ComponentFixture, TestBed} from '@angular/core/testing';

import {Parser} from './parser';

describe('Parser', () => {
  let component: Parser;
  let fixture: ComponentFixture<Parser>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      imports: [Parser]
    })
    .compileComponents();

    fixture = TestBed.createComponent(Parser);
    component = fixture.componentInstance;
    await fixture.whenStable();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
