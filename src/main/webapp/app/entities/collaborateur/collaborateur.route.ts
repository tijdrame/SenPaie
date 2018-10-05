import { Injectable } from '@angular/core';
import { Resolve, ActivatedRouteSnapshot, RouterStateSnapshot, Routes } from '@angular/router';
import { JhiPaginationUtil } from 'ng-jhipster';

import { UserRouteAccessService } from '../../shared';
import { CollaborateurComponent } from './collaborateur.component';
import { CollaborateurDetailComponent } from './collaborateur-detail.component';
import { CollaborateurPopupComponent } from './collaborateur-dialog.component';
import { CollaborateurDeletePopupComponent } from './collaborateur-delete-dialog.component';

@Injectable()
export class CollaborateurResolvePagingParams implements Resolve<any> {

    constructor(private paginationUtil: JhiPaginationUtil) {}

    resolve(route: ActivatedRouteSnapshot, state: RouterStateSnapshot) {
        const page = route.queryParams['page'] ? route.queryParams['page'] : '1';
        const sort = route.queryParams['sort'] ? route.queryParams['sort'] : 'id,asc';
        return {
            page: this.paginationUtil.parsePage(page),
            predicate: this.paginationUtil.parsePredicate(sort),
            ascending: this.paginationUtil.parseAscending(sort)
      };
    }
}

export const collaborateurRoute: Routes = [
    {
        path: 'collaborateur',
        component: CollaborateurComponent,
        resolve: {
            'pagingParams': CollaborateurResolvePagingParams
        },
        data: {
            authorities: ['ROLE_RH', 'ROLE_ADMIN', 'ROLE_DG'],
            pageTitle: 'senPaieApp.collaborateur.home.title'
        },
        canActivate: [UserRouteAccessService]
    }, {
        path: 'collaborateur/:id',
        component: CollaborateurDetailComponent,
        data: {
            authorities: ['ROLE_RH', 'ROLE_ADMIN', 'ROLE_DG'],
            pageTitle: 'senPaieApp.collaborateur.home.title'
        },
        canActivate: [UserRouteAccessService]
    }
];

export const collaborateurPopupRoute: Routes = [
    {
        path: 'collaborateur-new',
        component: CollaborateurPopupComponent,
        data: {
            authorities: ['ROLE_RH', 'ROLE_ADMIN'],
            pageTitle: 'senPaieApp.collaborateur.home.title'
        },
        canActivate: [UserRouteAccessService],
        outlet: 'popup'
    },
    {
        path: 'collaborateur/:id/edit',
        component: CollaborateurPopupComponent,
        data: {
            authorities: ['ROLE_RH', 'ROLE_ADMIN'],
            pageTitle: 'senPaieApp.collaborateur.home.title'
        },
        canActivate: [UserRouteAccessService],
        outlet: 'popup'
    },
    {
        path: 'collaborateur/:id/delete',
        component: CollaborateurDeletePopupComponent,
        data: {
            authorities: ['ROLE_RH', 'ROLE_ADMIN'],
            pageTitle: 'senPaieApp.collaborateur.home.title'
        },
        canActivate: [UserRouteAccessService],
        outlet: 'popup'
    }
];
