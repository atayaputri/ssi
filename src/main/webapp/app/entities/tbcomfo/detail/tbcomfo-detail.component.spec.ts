import { ComponentFixture, TestBed } from '@angular/core/testing';
import { ActivatedRoute } from '@angular/router';
import { of } from 'rxjs';

import { TBCOMFODetailComponent } from './tbcomfo-detail.component';

describe('TBCOMFO Management Detail Component', () => {
  let comp: TBCOMFODetailComponent;
  let fixture: ComponentFixture<TBCOMFODetailComponent>;

  beforeEach(() => {
    TestBed.configureTestingModule({
      declarations: [TBCOMFODetailComponent],
      providers: [
        {
          provide: ActivatedRoute,
          useValue: { data: of({ tBCOMFO: { id: 123 } }) },
        },
      ],
    })
      .overrideTemplate(TBCOMFODetailComponent, '')
      .compileComponents();
    fixture = TestBed.createComponent(TBCOMFODetailComponent);
    comp = fixture.componentInstance;
  });

  describe('OnInit', () => {
    it('Should load tBCOMFO on init', () => {
      // WHEN
      comp.ngOnInit();

      // THEN
      expect(comp.tBCOMFO).toEqual(expect.objectContaining({ id: 123 }));
    });
  });
});
