import { Injectable } from '@angular/core';
import { Resolve, ActivatedRouteSnapshot, RouterStateSnapshot, Routes } from '@angular/router';
import { JhiPaginationUtil } from 'ng-jhipster';

import { UserRouteAccessService } from '../../shared';
import { TypeContratComponent } from './type-contrat.component';
import { TypeContratDetailComponent } from './type-contrat-detail.component';
import { TypeContratPopupComponent } from './type-contrat-dialog.component';
import { TypeContratDeletePopupComponent } from './type-contrat-delete-dialog.component';

@Injectable()
export class TypeContratResolvePagingParams implements Resolve<any> {

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

export const typeContratRoute: Routes = [
    {
        path: 'type-contrat',
        component: TypeContratComponent,
        resolve: {
            'pagingParams': TypeContratResolvePagingParams
        },
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'senPaieApp.typeContrat.home.title'
        },
        canActivate: [UserRouteAccessService]
    }, {
        path: 'type-contrat/:id',
        component: TypeContratDetailComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'senPaieApp.typeContrat.home.title'
        },
        canActivate: [UserRouteAccessService]
    }
];

export const typeContratPopupRoute: Routes = [
    {
        path: 'type-contrat-new',
        component: TypeContratPopupComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'senPaieApp.typeContrat.home.title'
        },
        canActivate: [UserRouteAccessService],
        outlet: 'popup'
    },
    {
        path: 'type-contrat/:id/edit',
        component: TypeContratPopupComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'senPaieApp.typeContrat.home.title'
        },
        canActivate: [UserRouteAccessService],
        outlet: 'popup'
    },
    {
        path: 'type-contrat/:id/delete',
        component: TypeContratDeletePopupComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'senPaieApp.typeContrat.home.title'
        },
        canActivate: [UserRouteAccessService],
        outlet: 'popup'
    }
];
