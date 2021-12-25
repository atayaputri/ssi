import { ComponentFixture, TestBed } from '@angular/core/testing';
import { ActivatedRoute } from '@angular/router';
import { of } from 'rxjs';

import { TBPARTDetailComponent } from './tbpart-detail.component';

describe('TBPART Management Detail Component', () => {
  let comp: TBPARTDetailComponent;
  let fixture: ComponentFixture<TBPARTDetailComponent>;

  beforeEach(() => {
    TestBed.configureTestingModule({
      declarations: [TBPARTDetailComponent],
      providers: [
        {
          provide: ActivatedRoute,
          useValue: { data: of({ tBPART: { tpacode: 'ABC' } }) },
        },
      ],
    })
      .overrideTemplate(TBPARTDetailComponent, '')
      .compileComponents();
    fixture = TestBed.createComponent(TBPARTDetailComponent);
    comp = fixture.componentInstance;
  });

  describe('OnInit', () => {
    it('Should load tBPART on init', () => {
      // WHEN
      comp.ngOnInit();

      // THEN
      expect(comp.tBPART).toEqual(expect.objectContaining({ tpacode: 'ABC' }));
    });
  });
});
