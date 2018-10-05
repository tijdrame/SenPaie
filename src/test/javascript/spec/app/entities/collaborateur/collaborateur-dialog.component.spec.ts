/* tslint:disable max-line-length */
import { ComponentFixture, TestBed, async, inject, fakeAsync, tick } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { Observable } from 'rxjs/Observable';
import { JhiEventManager } from 'ng-jhipster';

import { SenPaieTestModule } from '../../../test.module';
import { CollaborateurDialogComponent } from '../../../../../../main/webapp/app/entities/collaborateur/collaborateur-dialog.component';
import { CollaborateurService } from '../../../../../../main/webapp/app/entities/collaborateur/collaborateur.service';
import { Collaborateur } from '../../../../../../main/webapp/app/entities/collaborateur/collaborateur.model';
import { FonctionService } from '../../../../../../main/webapp/app/entities/fonction';
import { CategorieService } from '../../../../../../main/webapp/app/entities/categorie';
import { NationaliteService } from '../../../../../../main/webapp/app/entities/nationalite';
import { StatutService } from '../../../../../../main/webapp/app/entities/statut';
import { SituationMatrimonialeService } from '../../../../../../main/webapp/app/entities/situation-matrimoniale';
import { TypeContratService } from '../../../../../../main/webapp/app/entities/type-contrat';
import { UserService } from '../../../../../../main/webapp/app/shared';
import { RegimeService } from '../../../../../../main/webapp/app/entities/regime';

describe('Component Tests', () => {

    describe('Collaborateur Management Dialog Component', () => {
        let comp: CollaborateurDialogComponent;
        let fixture: ComponentFixture<CollaborateurDialogComponent>;
        let service: CollaborateurService;
        let mockEventManager: any;
        let mockActiveModal: any;

        beforeEach(async(() => {
            TestBed.configureTestingModule({
                imports: [SenPaieTestModule],
                declarations: [CollaborateurDialogComponent],
                providers: [
                    FonctionService,
                    CategorieService,
                    NationaliteService,
                    StatutService,
                    SituationMatrimonialeService,
                    TypeContratService,
                    UserService,
                    RegimeService,
                    CollaborateurService
                ]
            })
            .overrideTemplate(CollaborateurDialogComponent, '')
            .compileComponents();
        }));

        beforeEach(() => {
            fixture = TestBed.createComponent(CollaborateurDialogComponent);
            comp = fixture.componentInstance;
            service = fixture.debugElement.injector.get(CollaborateurService);
            mockEventManager = fixture.debugElement.injector.get(JhiEventManager);
            mockActiveModal = fixture.debugElement.injector.get(NgbActiveModal);
        });

        describe('save', () => {
            it('Should call update service on save for existing entity',
                inject([],
                    fakeAsync(() => {
                        // GIVEN
                        const entity = new Collaborateur(123);
                        spyOn(service, 'update').and.returnValue(Observable.of(new HttpResponse({body: entity})));
                        comp.collaborateur = entity;
                        // WHEN
                        comp.save();
                        tick(); // simulate async

                        // THEN
                        expect(service.update).toHaveBeenCalledWith(entity);
                        expect(comp.isSaving).toEqual(false);
                        expect(mockEventManager.broadcastSpy).toHaveBeenCalledWith({ name: 'collaborateurListModification', content: 'OK'});
                        expect(mockActiveModal.dismissSpy).toHaveBeenCalled();
                    })
                )
            );

            it('Should call create service on save for new entity',
                inject([],
                    fakeAsync(() => {
                        // GIVEN
                        const entity = new Collaborateur();
                        spyOn(service, 'create').and.returnValue(Observable.of(new HttpResponse({body: entity})));
                        comp.collaborateur = entity;
                        // WHEN
                        comp.save();
                        tick(); // simulate async

                        // THEN
                        expect(service.create).toHaveBeenCalledWith(entity);
                        expect(comp.isSaving).toEqual(false);
                        expect(mockEventManager.broadcastSpy).toHaveBeenCalledWith({ name: 'collaborateurListModification', content: 'OK'});
                        expect(mockActiveModal.dismissSpy).toHaveBeenCalled();
                    })
                )
            );
        });
    });

});
