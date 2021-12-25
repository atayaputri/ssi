import { ComponentFixture, TestBed } from '@angular/core/testing';
import { ActivatedRoute } from '@angular/router';
import { of } from 'rxjs';

import { TBNEGDetailComponent } from './tbneg-detail.component';

describe('TBNEG Management Detail Component', () => {
  let comp: TBNEGDetailComponent;
  let fixture: ComponentFixture<TBNEGDetailComponent>;

  beforeEach(() => {
    TestBed.configureTestingModule({
      declarations: [TBNEGDetailComponent],
      providers: [
        {
          provide: ActivatedRoute,
          useValue: { data: of({ tBNEG: { negcod: 'ABC' } }) },
        },
      ],
    })
      .overrideTemplate(TBNEGDetailComponent, '')
      .compileComponents();
    fixture = TestBed.createComponent(TBNEGDetailComponent);
    comp = fixture.componentInstance;
  });

  describe('OnInit', () => {
    it('Should load tBNEG on init', () => {
      // WHEN
      comp.ngOnInit();

      // THEN
      expect(comp.tBNEG).toEqual(expect.objectContaining({ negcod: 'ABC' }));
    });
  });
});
