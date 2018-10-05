/* tslint:disable max-line-length */
import { ComponentFixture, TestBed, async } from '@angular/core/testing';
import { Observable } from 'rxjs/Observable';
import { HttpHeaders, HttpResponse } from '@angular/common/http';

import { SenPaieTestModule } from '../../../test.module';
import { NationaliteComponent } from '../../../../../../main/webapp/app/entities/nationalite/nationalite.component';
import { NationaliteService } from '../../../../../../main/webapp/app/entities/nationalite/nationalite.service';
import { Nationalite } from '../../../../../../main/webapp/app/entities/nationalite/nationalite.model';

describe('Component Tests', () => {

    describe('Nationalite Management Component', () => {
        let comp: NationaliteComponent;
        let fixture: ComponentFixture<NationaliteComponent>;
        let service: NationaliteService;

        beforeEach(async(() => {
            TestBed.configureTestingModule({
                imports: [SenPaieTestModule],
                declarations: [NationaliteComponent],
                providers: [
                    NationaliteService
                ]
            })
            .overrideTemplate(NationaliteComponent, '')
            .compileComponents();
        }));

        beforeEach(() => {
            fixture = TestBed.createComponent(NationaliteComponent);
            comp = fixture.componentInstance;
            service = fixture.debugElement.injector.get(NationaliteService);
        });

        describe('OnInit', () => {
            it('Should call load all on init', () => {
                // GIVEN
                const headers = new HttpHeaders().append('link', 'link;link');
                spyOn(service, 'query').and.returnValue(Observable.of(new HttpResponse({
                    body: [new Nationalite(123)],
                    headers
                })));

                // WHEN
                comp.ngOnInit();

                // THEN
                expect(service.query).toHaveBeenCalled();
                expect(comp.nationalites[0]).toEqual(jasmine.objectContaining({id: 123}));
            });
        });
    });

});
