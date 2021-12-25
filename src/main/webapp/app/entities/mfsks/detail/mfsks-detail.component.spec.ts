import { ComponentFixture, TestBed } from '@angular/core/testing';
import { ActivatedRoute } from '@angular/router';
import { of } from 'rxjs';

import { MFSKSDetailComponent } from './mfsks-detail.component';

describe('MFSKS Management Detail Component', () => {
  let comp: MFSKSDetailComponent;
  let fixture: ComponentFixture<MFSKSDetailComponent>;

  beforeEach(() => {
    TestBed.configureTestingModule({
      declarations: [MFSKSDetailComponent],
      providers: [
        {
          provide: ActivatedRoute,
          useValue: { data: of({ mFSKS: { skno: 123 } }) },
        },
      ],
    })
      .overrideTemplate(MFSKSDetailComponent, '')
      .compileComponents();
    fixture = TestBed.createComponent(MFSKSDetailComponent);
    comp = fixture.componentInstance;
  });

  describe('OnInit', () => {
    it('Should load mFSKS on init', () => {
      // WHEN
      comp.ngOnInit();

      // THEN
      expect(comp.mFSKS).toEqual(expect.objectContaining({ skno: 123 }));
    });
  });
});
