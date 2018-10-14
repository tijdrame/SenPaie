/* tslint:disable max-line-length */
import { ComponentFixture, TestBed, async, inject, fakeAsync, tick } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { Observable } from 'rxjs/Observable';
import { JhiEventManager } from 'ng-jhipster';

import { SenPaieTestModule } from '../../../test.module';
import { DemandeCongeDialogComponent } from '../../../../../../main/webapp/app/entities/demande-conge/demande-conge-dialog.component';
import { DemandeCongeService } from '../../../../../../main/webapp/app/entities/demande-conge/demande-conge.service';
import { DemandeConge } from '../../../../../../main/webapp/app/entities/demande-conge/demande-conge.model';
import { StatutDemandeService } from '../../../../../../main/webapp/app/entities/statut-demande';
import { CollaborateurService } from '../../../../../../main/webapp/app/entities/collaborateur';
import { UserService } from '../../../../../../main/webapp/app/shared';
import { TypeAbsenceService } from '../../../../../../main/webapp/app/entities/type-absence';

describe('Component Tests', () => {

    describe('DemandeConge Management Dialog Component', () => {
        let comp: DemandeCongeDialogComponent;
        let fixture: ComponentFixture<DemandeCongeDialogComponent>;
        let service: DemandeCongeService;
        let mockEventManager: any;
        let mockActiveModal: any;

        beforeEach(async(() => {
            TestBed.configureTestingModule({
                imports: [SenPaieTestModule],
                declarations: [DemandeCongeDialogComponent],
                providers: [
                    StatutDemandeService,
                    CollaborateurService,
                    UserService,
                    TypeAbsenceService,
                    DemandeCongeService
                ]
            })
            .overrideTemplate(DemandeCongeDialogComponent, '')
            .compileComponents();
        }));

        beforeEach(() => {
            fixture = TestBed.createComponent(DemandeCongeDialogComponent);
            comp = fixture.componentInstance;
            service = fixture.debugElement.injector.get(DemandeCongeService);
            mockEventManager = fixture.debugElement.injector.get(JhiEventManager);
            mockActiveModal = fixture.debugElement.injector.get(NgbActiveModal);
        });

        describe('save', () => {
            it('Should call update service on save for existing entity',
                inject([],
                    fakeAsync(() => {
                        // GIVEN
                        const entity = new DemandeConge(123);
                        spyOn(service, 'update').and.returnValue(Observable.of(new HttpResponse({body: entity})));
                        comp.demandeConge = entity;
                        // WHEN
                        comp.save();
                        tick(); // simulate async

                        // THEN
                        expect(service.update).toHaveBeenCalledWith(entity);
                        expect(comp.isSaving).toEqual(false);
                        expect(mockEventManager.broadcastSpy).toHaveBeenCalledWith({ name: 'demandeCongeListModification', content: 'OK'});
                        expect(mockActiveModal.dismissSpy).toHaveBeenCalled();
                    })
                )
            );

            it('Should call create service on save for new entity',
                inject([],
                    fakeAsync(() => {
                        // GIVEN
                        const entity = new DemandeConge();
                        spyOn(service, 'create').and.returnValue(Observable.of(new HttpResponse({body: entity})));
                        comp.demandeConge = entity;
                        // WHEN
                        comp.save();
                        tick(); // simulate async

                        // THEN
                        expect(service.create).toHaveBeenCalledWith(entity);
                        expect(comp.isSaving).toEqual(false);
                        expect(mockEventManager.broadcastSpy).toHaveBeenCalledWith({ name: 'demandeCongeListModification', content: 'OK'});
                        expect(mockActiveModal.dismissSpy).toHaveBeenCalled();
                    })
                )
            );
        });
    });

});
