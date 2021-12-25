import { ComponentFixture, TestBed } from '@angular/core/testing';
import { ActivatedRoute } from '@angular/router';
import { of } from 'rxjs';

import { TBPROVDetailComponent } from './tbprov-detail.component';

describe('TBPROV Management Detail Component', () => {
  let comp: TBPROVDetailComponent;
  let fixture: ComponentFixture<TBPROVDetailComponent>;

  beforeEach(() => {
    TestBed.configureTestingModule({
      declarations: [TBPROVDetailComponent],
      providers: [
        {
          provide: ActivatedRoute,
          useValue: { data: of({ tBPROV: { provcod: 'ABC' } }) },
        },
      ],
    })
      .overrideTemplate(TBPROVDetailComponent, '')
      .compileComponents();
    fixture = TestBed.createComponent(TBPROVDetailComponent);
    comp = fixture.componentInstance;
  });

  describe('OnInit', () => {
    it('Should load tBPROV on init', () => {
      // WHEN
      comp.ngOnInit();

      // THEN
      expect(comp.tBPROV).toEqual(expect.objectContaining({ provcod: 'ABC' }));
    });
  });
});
