import { ComponentFixture, TestBed } from '@angular/core/testing';
import { ActivatedRoute } from '@angular/router';
import { of } from 'rxjs';

import { TABFEEDetailComponent } from './tabfee-detail.component';

describe('TABFEE Management Detail Component', () => {
  let comp: TABFEEDetailComponent;
  let fixture: ComponentFixture<TABFEEDetailComponent>;

  beforeEach(() => {
    TestBed.configureTestingModule({
      declarations: [TABFEEDetailComponent],
      providers: [
        {
          provide: ActivatedRoute,
          useValue: { data: of({ tABFEE: { id: 123 } }) },
        },
      ],
    })
      .overrideTemplate(TABFEEDetailComponent, '')
      .compileComponents();
    fixture = TestBed.createComponent(TABFEEDetailComponent);
    comp = fixture.componentInstance;
  });

  describe('OnInit', () => {
    it('Should load tABFEE on init', () => {
      // WHEN
      comp.ngOnInit();

      // THEN
      expect(comp.tABFEE).toEqual(expect.objectContaining({ id: 123 }));
    });
  });
});
