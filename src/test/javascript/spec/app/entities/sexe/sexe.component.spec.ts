/* tslint:disable max-line-length */
import { ComponentFixture, TestBed, async } from '@angular/core/testing';
import { Observable } from 'rxjs/Observable';
import { HttpHeaders, HttpResponse } from '@angular/common/http';

import { SenPaieTestModule } from '../../../test.module';
import { SexeComponent } from '../../../../../../main/webapp/app/entities/sexe/sexe.component';
import { SexeService } from '../../../../../../main/webapp/app/entities/sexe/sexe.service';
import { Sexe } from '../../../../../../main/webapp/app/entities/sexe/sexe.model';

describe('Component Tests', () => {

    describe('Sexe Management Component', () => {
        let comp: SexeComponent;
        let fixture: ComponentFixture<SexeComponent>;
        let service: SexeService;

        beforeEach(async(() => {
            TestBed.configureTestingModule({
                imports: [SenPaieTestModule],
                declarations: [SexeComponent],
                providers: [
                    SexeService
                ]
            })
            .overrideTemplate(SexeComponent, '')
            .compileComponents();
        }));

        beforeEach(() => {
            fixture = TestBed.createComponent(SexeComponent);
            comp = fixture.componentInstance;
            service = fixture.debugElement.injector.get(SexeService);
        });

        describe('OnInit', () => {
            it('Should call load all on init', () => {
                // GIVEN
                const headers = new HttpHeaders().append('link', 'link;link');
                spyOn(service, 'query').and.returnValue(Observable.of(new HttpResponse({
                    body: [new Sexe(123)],
                    headers
                })));

                // WHEN
                comp.ngOnInit();

                // THEN
                expect(service.query).toHaveBeenCalled();
                expect(comp.sexes[0]).toEqual(jasmine.objectContaining({id: 123}));
            });
        });
    });

});
