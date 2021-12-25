import { ComponentFixture, TestBed } from '@angular/core/testing';
import { ActivatedRoute } from '@angular/router';
import { of } from 'rxjs';

import { TBTYPSDetailComponent } from './tbtyps-detail.component';

describe('TBTYPS Management Detail Component', () => {
  let comp: TBTYPSDetailComponent;
  let fixture: ComponentFixture<TBTYPSDetailComponent>;

  beforeEach(() => {
    TestBed.configureTestingModule({
      declarations: [TBTYPSDetailComponent],
      providers: [
        {
          provide: ActivatedRoute,
          useValue: { data: of({ tBTYPS: { tpscod: 'ABC' } }) },
        },
      ],
    })
      .overrideTemplate(TBTYPSDetailComponent, '')
      .compileComponents();
    fixture = TestBed.createComponent(TBTYPSDetailComponent);
    comp = fixture.componentInstance;
  });

  describe('OnInit', () => {
    it('Should load tBTYPS on init', () => {
      // WHEN
      comp.ngOnInit();

      // THEN
      expect(comp.tBTYPS).toEqual(expect.objectContaining({ tpscod: 'ABC' }));
    });
  });
});
