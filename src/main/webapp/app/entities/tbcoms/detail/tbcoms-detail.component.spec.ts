import { ComponentFixture, TestBed } from '@angular/core/testing';
import { ActivatedRoute } from '@angular/router';
import { of } from 'rxjs';

import { TBCOMSDetailComponent } from './tbcoms-detail.component';

describe('TBCOMS Management Detail Component', () => {
  let comp: TBCOMSDetailComponent;
  let fixture: ComponentFixture<TBCOMSDetailComponent>;

  beforeEach(() => {
    TestBed.configureTestingModule({
      declarations: [TBCOMSDetailComponent],
      providers: [
        {
          provide: ActivatedRoute,
          useValue: { data: of({ tBCOMS: { cocode: 'ABC' } }) },
        },
      ],
    })
      .overrideTemplate(TBCOMSDetailComponent, '')
      .compileComponents();
    fixture = TestBed.createComponent(TBCOMSDetailComponent);
    comp = fixture.componentInstance;
  });

  describe('OnInit', () => {
    it('Should load tBCOMS on init', () => {
      // WHEN
      comp.ngOnInit();

      // THEN
      expect(comp.tBCOMS).toEqual(expect.objectContaining({ cocode: 'ABC' }));
    });
  });
});
