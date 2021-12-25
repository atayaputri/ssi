import { ComponentFixture, TestBed } from '@angular/core/testing';
import { ActivatedRoute } from '@angular/router';
import { of } from 'rxjs';

import { TBHDRDetailComponent } from './tbhdr-detail.component';

describe('TBHDR Management Detail Component', () => {
  let comp: TBHDRDetailComponent;
  let fixture: ComponentFixture<TBHDRDetailComponent>;

  beforeEach(() => {
    TestBed.configureTestingModule({
      declarations: [TBHDRDetailComponent],
      providers: [
        {
          provide: ActivatedRoute,
          useValue: { data: of({ tBHDR: { thno: 123 } }) },
        },
      ],
    })
      .overrideTemplate(TBHDRDetailComponent, '')
      .compileComponents();
    fixture = TestBed.createComponent(TBHDRDetailComponent);
    comp = fixture.componentInstance;
  });

  describe('OnInit', () => {
    it('Should load tBHDR on init', () => {
      // WHEN
      comp.ngOnInit();

      // THEN
      expect(comp.tBHDR).toEqual(expect.objectContaining({ thno: 123 }));
    });
  });
});
