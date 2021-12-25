import { ComponentFixture, TestBed } from '@angular/core/testing';
import { ActivatedRoute } from '@angular/router';
import { of } from 'rxjs';

import { TBKABDetailComponent } from './tbkab-detail.component';

describe('TBKAB Management Detail Component', () => {
  let comp: TBKABDetailComponent;
  let fixture: ComponentFixture<TBKABDetailComponent>;

  beforeEach(() => {
    TestBed.configureTestingModule({
      declarations: [TBKABDetailComponent],
      providers: [
        {
          provide: ActivatedRoute,
          useValue: { data: of({ tBKAB: { kabcod: 'ABC' } }) },
        },
      ],
    })
      .overrideTemplate(TBKABDetailComponent, '')
      .compileComponents();
    fixture = TestBed.createComponent(TBKABDetailComponent);
    comp = fixture.componentInstance;
  });

  describe('OnInit', () => {
    it('Should load tBKAB on init', () => {
      // WHEN
      comp.ngOnInit();

      // THEN
      expect(comp.tBKAB).toEqual(expect.objectContaining({ kabcod: 'ABC' }));
    });
  });
});
