/* tslint:disable max-line-length */
import { ComponentFixture, TestBed, async } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs/Observable';

import { SenPaieTestModule } from '../../../test.module';
import { CollaborateurDetailComponent } from '../../../../../../main/webapp/app/entities/collaborateur/collaborateur-detail.component';
import { CollaborateurService } from '../../../../../../main/webapp/app/entities/collaborateur/collaborateur.service';
import { Collaborateur } from '../../../../../../main/webapp/app/entities/collaborateur/collaborateur.model';

describe('Component Tests', () => {

    describe('Collaborateur Management Detail Component', () => {
        let comp: CollaborateurDetailComponent;
        let fixture: ComponentFixture<CollaborateurDetailComponent>;
        let service: CollaborateurService;

        beforeEach(async(() => {
            TestBed.configureTestingModule({
                imports: [SenPaieTestModule],
                declarations: [CollaborateurDetailComponent],
                providers: [
                    CollaborateurService
                ]
            })
            .overrideTemplate(CollaborateurDetailComponent, '')
            .compileComponents();
        }));

        beforeEach(() => {
            fixture = TestBed.createComponent(CollaborateurDetailComponent);
            comp = fixture.componentInstance;
            service = fixture.debugElement.injector.get(CollaborateurService);
        });

        describe('OnInit', () => {
            it('Should call load all on init', () => {
                // GIVEN

                spyOn(service, 'find').and.returnValue(Observable.of(new HttpResponse({
                    body: new Collaborateur(123)
                })));

                // WHEN
                comp.ngOnInit();

                // THEN
                expect(service.find).toHaveBeenCalledWith(123);
                expect(comp.collaborateur).toEqual(jasmine.objectContaining({id: 123}));
            });
        });
    });

});
