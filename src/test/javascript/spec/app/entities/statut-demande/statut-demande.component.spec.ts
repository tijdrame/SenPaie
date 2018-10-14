/* tslint:disable max-line-length */
import { ComponentFixture, TestBed, async } from '@angular/core/testing';
import { Observable } from 'rxjs/Observable';
import { HttpHeaders, HttpResponse } from '@angular/common/http';

import { SenPaieTestModule } from '../../../test.module';
import { StatutDemandeComponent } from '../../../../../../main/webapp/app/entities/statut-demande/statut-demande.component';
import { StatutDemandeService } from '../../../../../../main/webapp/app/entities/statut-demande/statut-demande.service';
import { StatutDemande } from '../../../../../../main/webapp/app/entities/statut-demande/statut-demande.model';

describe('Component Tests', () => {

    describe('StatutDemande Management Component', () => {
        let comp: StatutDemandeComponent;
        let fixture: ComponentFixture<StatutDemandeComponent>;
        let service: StatutDemandeService;

        beforeEach(async(() => {
            TestBed.configureTestingModule({
                imports: [SenPaieTestModule],
                declarations: [StatutDemandeComponent],
                providers: [
                    StatutDemandeService
                ]
            })
            .overrideTemplate(StatutDemandeComponent, '')
            .compileComponents();
        }));

        beforeEach(() => {
            fixture = TestBed.createComponent(StatutDemandeComponent);
            comp = fixture.componentInstance;
            service = fixture.debugElement.injector.get(StatutDemandeService);
        });

        describe('OnInit', () => {
            it('Should call load all on init', () => {
                // GIVEN
                const headers = new HttpHeaders().append('link', 'link;link');
                spyOn(service, 'query').and.returnValue(Observable.of(new HttpResponse({
                    body: [new StatutDemande(123)],
                    headers
                })));

                // WHEN
                comp.ngOnInit();

                // THEN
                expect(service.query).toHaveBeenCalled();
                expect(comp.statutDemandes[0]).toEqual(jasmine.objectContaining({id: 123}));
            });
        });
    });

});
