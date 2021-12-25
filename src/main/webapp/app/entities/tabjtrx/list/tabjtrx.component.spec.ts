import { ComponentFixture, TestBed } from '@angular/core/testing';
import { HttpHeaders, HttpResponse } from '@angular/common/http';
import { HttpClientTestingModule } from '@angular/common/http/testing';
import { of } from 'rxjs';

import { TABJTRXService } from '../service/tabjtrx.service';

import { TABJTRXComponent } from './tabjtrx.component';

describe('TABJTRX Management Component', () => {
  let comp: TABJTRXComponent;
  let fixture: ComponentFixture<TABJTRXComponent>;
  let service: TABJTRXService;

  beforeEach(() => {
    TestBed.configureTestingModule({
      imports: [HttpClientTestingModule],
      declarations: [TABJTRXComponent],
    })
      .overrideTemplate(TABJTRXComponent, '')
      .compileComponents();

    fixture = TestBed.createComponent(TABJTRXComponent);
    comp = fixture.componentInstance;
    service = TestBed.inject(TABJTRXService);

    const headers = new HttpHeaders();
    jest.spyOn(service, 'query').mockReturnValue(
      of(
        new HttpResponse({
          body: [{ jtjntx: 'ABC' }],
          headers,
        })
      )
    );
  });

  it('Should call load all on init', () => {
    // WHEN
    comp.ngOnInit();

    // THEN
    expect(service.query).toHaveBeenCalled();
    expect(comp.tABJTRXES?.[0]).toEqual(expect.objectContaining({ jtjntx: 'ABC' }));
  });
});
