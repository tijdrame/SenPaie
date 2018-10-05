/* tslint:disable max-line-length */
import { ComponentFixture, TestBed, async } from '@angular/core/testing';
import { Observable } from 'rxjs/Observable';
import { HttpHeaders, HttpResponse } from '@angular/common/http';

import { SenPaieTestModule } from '../../../test.module';
import { FonctionComponent } from '../../../../../../main/webapp/app/entities/fonction/fonction.component';
import { FonctionService } from '../../../../../../main/webapp/app/entities/fonction/fonction.service';
import { Fonction } from '../../../../../../main/webapp/app/entities/fonction/fonction.model';

describe('Component Tests', () => {

    describe('Fonction Management Component', () => {
        let comp: FonctionComponent;
        let fixture: ComponentFixture<FonctionComponent>;
        let service: FonctionService;

        beforeEach(async(() => {
            TestBed.configureTestingModule({
                imports: [SenPaieTestModule],
                declarations: [FonctionComponent],
                providers: [
                    FonctionService
                ]
            })
            .overrideTemplate(FonctionComponent, '')
            .compileComponents();
        }));

        beforeEach(() => {
            fixture = TestBed.createComponent(FonctionComponent);
            comp = fixture.componentInstance;
            service = fixture.debugElement.injector.get(FonctionService);
        });

        describe('OnInit', () => {
            it('Should call load all on init', () => {
                // GIVEN
                const headers = new HttpHeaders().append('link', 'link;link');
                spyOn(service, 'query').and.returnValue(Observable.of(new HttpResponse({
                    body: [new Fonction(123)],
                    headers
                })));

                // WHEN
                comp.ngOnInit();

                // THEN
                expect(service.query).toHaveBeenCalled();
                expect(comp.fonctions[0]).toEqual(jasmine.objectContaining({id: 123}));
            });
        });
    });

});
