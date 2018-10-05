/* tslint:disable max-line-length */
import { ComponentFixture, TestBed, async } from '@angular/core/testing';
import { Observable } from 'rxjs/Observable';
import { HttpHeaders, HttpResponse } from '@angular/common/http';

import { SenPaieTestModule } from '../../../test.module';
import { StatutComponent } from '../../../../../../main/webapp/app/entities/statut/statut.component';
import { StatutService } from '../../../../../../main/webapp/app/entities/statut/statut.service';
import { Statut } from '../../../../../../main/webapp/app/entities/statut/statut.model';

describe('Component Tests', () => {

    describe('Statut Management Component', () => {
        let comp: StatutComponent;
        let fixture: ComponentFixture<StatutComponent>;
        let service: StatutService;

        beforeEach(async(() => {
            TestBed.configureTestingModule({
                imports: [SenPaieTestModule],
                declarations: [StatutComponent],
                providers: [
                    StatutService
                ]
            })
            .overrideTemplate(StatutComponent, '')
            .compileComponents();
        }));

        beforeEach(() => {
            fixture = TestBed.createComponent(StatutComponent);
            comp = fixture.componentInstance;
            service = fixture.debugElement.injector.get(StatutService);
        });

        describe('OnInit', () => {
            it('Should call load all on init', () => {
                // GIVEN
                const headers = new HttpHeaders().append('link', 'link;link');
                spyOn(service, 'query').and.returnValue(Observable.of(new HttpResponse({
                    body: [new Statut(123)],
                    headers
                })));

                // WHEN
                comp.ngOnInit();

                // THEN
                expect(service.query).toHaveBeenCalled();
                expect(comp.statuts[0]).toEqual(jasmine.objectContaining({id: 123}));
            });
        });
    });

});
