/* tslint:disable max-line-length */
import { ComponentFixture, TestBed, async } from '@angular/core/testing';
import { Observable } from 'rxjs/Observable';
import { HttpHeaders, HttpResponse } from '@angular/common/http';

import { SenPaieTestModule } from '../../../test.module';
import { DemandeCongeComponent } from '../../../../../../main/webapp/app/entities/demande-conge/demande-conge.component';
import { DemandeCongeService } from '../../../../../../main/webapp/app/entities/demande-conge/demande-conge.service';
import { DemandeConge } from '../../../../../../main/webapp/app/entities/demande-conge/demande-conge.model';

describe('Component Tests', () => {

    describe('DemandeConge Management Component', () => {
        let comp: DemandeCongeComponent;
        let fixture: ComponentFixture<DemandeCongeComponent>;
        let service: DemandeCongeService;

        beforeEach(async(() => {
            TestBed.configureTestingModule({
                imports: [SenPaieTestModule],
                declarations: [DemandeCongeComponent],
                providers: [
                    DemandeCongeService
                ]
            })
            .overrideTemplate(DemandeCongeComponent, '')
            .compileComponents();
        }));

        beforeEach(() => {
            fixture = TestBed.createComponent(DemandeCongeComponent);
            comp = fixture.componentInstance;
            service = fixture.debugElement.injector.get(DemandeCongeService);
        });

        describe('OnInit', () => {
            it('Should call load all on init', () => {
                // GIVEN
                const headers = new HttpHeaders().append('link', 'link;link');
                spyOn(service, 'query').and.returnValue(Observable.of(new HttpResponse({
                    body: [new DemandeConge(123)],
                    headers
                })));

                // WHEN
                comp.ngOnInit();

                // THEN
                expect(service.query).toHaveBeenCalled();
                expect(comp.demandeConges[0]).toEqual(jasmine.objectContaining({id: 123}));
            });
        });
    });

});
