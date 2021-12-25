import { ComponentFixture, TestBed } from '@angular/core/testing';
import { ActivatedRoute } from '@angular/router';
import { of } from 'rxjs';

import { TBBROKERDetailComponent } from './tbbroker-detail.component';

describe('TBBROKER Management Detail Component', () => {
  let comp: TBBROKERDetailComponent;
  let fixture: ComponentFixture<TBBROKERDetailComponent>;

  beforeEach(() => {
    TestBed.configureTestingModule({
      declarations: [TBBROKERDetailComponent],
      providers: [
        {
          provide: ActivatedRoute,
          useValue: { data: of({ tBBROKER: { brcode: 'ABC' } }) },
        },
      ],
    })
      .overrideTemplate(TBBROKERDetailComponent, '')
      .compileComponents();
    fixture = TestBed.createComponent(TBBROKERDetailComponent);
    comp = fixture.componentInstance;
  });

  describe('OnInit', () => {
    it('Should load tBBROKER on init', () => {
      // WHEN
      comp.ngOnInit();

      // THEN
      expect(comp.tBBROKER).toEqual(expect.objectContaining({ brcode: 'ABC' }));
    });
  });
});
