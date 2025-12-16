import { ComponentFixture, TestBed } from '@angular/core/testing';

import { Serialization } from './serialization';

describe('Serialization', () => {
  let component: Serialization;
  let fixture: ComponentFixture<Serialization>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      imports: [Serialization]
    })
    .compileComponents();

    fixture = TestBed.createComponent(Serialization);
    component = fixture.componentInstance;
    await fixture.whenStable();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
