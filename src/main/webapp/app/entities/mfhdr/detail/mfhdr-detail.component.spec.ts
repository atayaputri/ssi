import { ComponentFixture, TestBed } from '@angular/core/testing';
import { ActivatedRoute } from '@angular/router';
import { of } from 'rxjs';

import { MFHDRDetailComponent } from './mfhdr-detail.component';

describe('MFHDR Management Detail Component', () => {
  let comp: MFHDRDetailComponent;
  let fixture: ComponentFixture<MFHDRDetailComponent>;

  beforeEach(() => {
    TestBed.configureTestingModule({
      declarations: [MFHDRDetailComponent],
      providers: [
        {
          provide: ActivatedRoute,
          useValue: { data: of({ mFHDR: { hdno: 123 } }) },
        },
      ],
    })
      .overrideTemplate(MFHDRDetailComponent, '')
      .compileComponents();
    fixture = TestBed.createComponent(MFHDRDetailComponent);
    comp = fixture.componentInstance;
  });

  describe('OnInit', () => {
    it('Should load mFHDR on init', () => {
      // WHEN
      comp.ngOnInit();

      // THEN
      expect(comp.mFHDR).toEqual(expect.objectContaining({ hdno: 123 }));
    });
  });
});
